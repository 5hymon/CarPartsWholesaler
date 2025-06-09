import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

interface CartItem {
  partId: number;
  partName: string;
  unitPrice: number;
  quantity: number;
}

@Component({
  selector: 'app-shopping-cart',
  standalone: true,               // <-- dodane
  imports: [CommonModule],         // <-- CommonModule potrzebne dla *ngIf, *ngFor, currency
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']  // <-- poprawiona nazwa
})
export class ShoppingCartComponent implements OnInit {
  cartItems: CartItem[] = [];

  ngOnInit(): void {
    this.loadCart();
  }

  saveCart(): void {
    localStorage.setItem('cart', JSON.stringify(this.cartItems));
  }

  loadCart(): void {
    const cartData = typeof window !== 'undefined' ? localStorage.getItem('cart') : null;
    this.cartItems = cartData ? JSON.parse(cartData) : [];
  }

  removeItem(partId: number): void {
    this.cartItems = this.cartItems.filter(item => item.partId !== partId);
    this.saveCart();
  }

  clearCart(): void {
    if(confirm('Czy na pewno chcesz wyczyścić koszyk?')){
      localStorage.removeItem('cart');
      this.cartItems = [];
    }
  }

  getTotalPrice(): number {
    return this.cartItems.reduce((sum, i) => sum + i.unitPrice * i.quantity, 0);
  }

  increaseQuantity(item: CartItem): void {
    item.quantity++;
    this.saveCart();
  }

  decreaseQuantity(item: CartItem): void {
    if (item.quantity > 1) {
      item.quantity--;
      this.saveCart();
    } else {
      if(confirm('Czy na pewno chcesz usunąć ten przedmiot z koszyka?')){
        this.removeItem(item.partId);
      }
    }
  }
}
