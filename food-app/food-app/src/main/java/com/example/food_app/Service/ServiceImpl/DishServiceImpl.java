package com.example.food_app.Service.ServiceImpl;

import com.example.food_app.Request.CreateDishRequest;
import com.example.food_app.Service.DishService;
import com.example.food_app.dto.DishDto;
import com.example.food_app.model.Address;
import com.example.food_app.model.Dish;
import com.example.food_app.model.User;
import com.example.food_app.repository.AddressRepository;
import com.example.food_app.repository.DishRepository;
import com.example.food_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    DishRepository dishRepository;
    AddressRepository addressRepository;
    UserRepository userRepository;

    @Override
    public Dish createDish(CreateDishRequest request, User user) throws Exception {
        //add address
        Address address = addressRepository.save(request.getAddress());

        // create Dish objects
        Dish dish = new Dish();
        dish.setAddress(address);
        dish.setContactInformation(request.getContactInformation());
        dish.setCuisineType(request.getCuisineType());
        dish.setDescription(request.getDescription());
        dish.setImages(request.getImages());
        dish.setName(request.getName());
        dish.setOpeningHour(request.getOpeningHour());
        dish.setRegistrationDate(LocalDateTime.now());
        dish.setOwner(user);
        return  dishRepository.save(dish);
    }

    @Override
    public Dish updateDish(Long dishId, CreateDishRequest updateDish) throws Exception {
        // find by id
        Dish dish = findDishById(dishId);
        if(dish.getCuisineType() != null){
            dish.setCuisineType(updateDish.getCuisineType());
        }
        if (dish.getDescription() != null){
            dish.setDescription(updateDish.getDescription());
        }
        if(dish.getName() != null){
            dish.setName(updateDish.getName());
        }
        return dishRepository.save(dish);
    }

    @Override
    public void deleteDish(Long dishId) throws Exception {
        Dish dish = findDishById(dishId);
        dishRepository.delete(dish);
    }

    @Override
    public List<Dish> getAllDish() {
        return dishRepository.findAll();
    }

    @Override
    public List<Dish> searchDish(String keyword) {
        return dishRepository.findBySearchQuery(keyword);
    }

    @Override
    public Dish findDishById(Long Id) throws Exception {
        Optional<Dish> opt = dishRepository.findById(Id);
        if(opt.isEmpty()){
            throw new Exception("Dish not found with Id"+Id);
        }
        return opt.get();
    }

    @Override
    public DishDto addToFavorite(Long dishId, User user) throws Exception {
        Dish dish = findDishById(dishId);

        //create dishdto
        DishDto dto = new DishDto();
        dto.setDescription(dish.getDescription());
        dto.setImages(dish.getImages());
        dto.setTitle(dish.getName());
        dto.setId(dishId);

        // if user favorite is present, its removed if not its added
        boolean isFavorited = false;
        List<DishDto>favorites = user.getFavorites();
        for(DishDto favorite : favorites) {
            if (favorite.getId().equals(dishId)) {
                isFavorited = true;
                break;
            }
        }
            if (isFavorited){
                favorites.removeIf(favorite -> favorite.getId().equals(dishId));
            }else {
                favorites.add(dto);
            }
        userRepository.save(user);
        return dto;
    }

    @Override
    public Dish updateDishStatues(Long Id) throws Exception {
        Dish dish = findDishById(Id);
        dish.setAvailable(!dish.isAvailable());
        return dishRepository.save(dish);
    }

    @Override
    public Dish getDishByUserId(Long userId) throws Exception {
        Dish dish = dishRepository.findByOwnerId(userId);
        if(dish == null){
            throw new Exception("Dish not found with owner id"+userId);
        }
        return dish;
    }
}
