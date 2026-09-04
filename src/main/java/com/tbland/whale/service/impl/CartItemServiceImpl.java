package com.tbland.whale.service.impl;

import com.tbland.whale.dto.record.CartItemRequest;
import com.tbland.whale.dto.record.CartItemResponse;
import com.tbland.whale.entity.Cart;
import com.tbland.whale.entity.CartItem;
import com.tbland.whale.entity.Product;
import com.tbland.whale.exception.ResourceNotFoundException;
import com.tbland.whale.mapper.CartItemMapper;
import com.tbland.whale.repository.CartItemRepository;
import com.tbland.whale.repository.CartRepository;
import com.tbland.whale.repository.ProductRepository;
import com.tbland.whale.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemMapper cartItemMapper;
    public CartItemServiceImpl(CartItemRepository cartItemRepository,
                               CartRepository cartRepository,
                               ProductRepository productRepository,
                               CartItemMapper cartItemMapper
    ){
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemMapper= cartItemMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<CartItemResponse> findAll() {
        return cartItemRepository.findAll()
                .stream()
                .map(cartItemMapper::toResponse)
                .toList();
    }

    @Override
    public CartItemResponse create(CartItemRequest request) {
        CartItem cartItem = cartItemMapper.toEntity(request);
        Cart cart = cartRepository.findById(request.cartId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy id giỏ hàng: "+request.cartId())
                        );
        Product product = productRepository.findById(request.productId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tim thấy id sản phẩm: "+request.productId())
                        );
        cartItem.setCart(cart);
        cartItem.setProduct(product);

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        return cartItemMapper.toResponse(savedCartItem);
    }

    @Override
    public CartItemResponse update(Integer id, CartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy id mục giỏ hàng: "+id)
                        );
        cartItem.setQuantity(request.quantity());

        Cart cart = cartRepository.findById(request.cartId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy giỏ hàng: "+request.cartId())
                        );
        Product product = productRepository.findById(request.productId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy sản phẩm: "+request.productId())
                        );

        cartItem.setCart(cart);
        cartItem.setProduct(product);

        CartItem cartItemUpdated = cartItemRepository.save(cartItem);

        return cartItemMapper.toResponse(cartItemUpdated);

    }

    @Override
    public CartItemResponse findById(Integer id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy id mục giỏ hàng: "+id)
                        );
        return cartItemMapper.toResponse(cartItem);

    }

    @Override
    public void delete(Integer id) {
        cartItemRepository.deleteById(id);
    }
}
