import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

interface CartItem {
  partId: number;
  partName: string;
  unitPrice: number;
  quantity: number;
}

@Component({
  selector: 'app-shopping-cart',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {
  cartItems: CartItem[] = [];
  paymentMethod: 'Przelew'|'Gotówka'|'Karta' = 'Przelew';

  private readonly ordersUrl = 'http://localhost:8080/orders';

  constructor(private http: HttpClient) {}

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

  placeOrder() {
    // wyciągamy email zalogowanego
    const email = localStorage.getItem('email');
    if (!email) {
      alert('Brak informacji o użytkowniku.');
      return;
    }

    // budujemy body jako x-www-form-urlencoded
    let body = new HttpParams()
      .set('customerEmailAddress', email)
      .set('paymentMethod', this.paymentMethod);

    // dla każdego itema dorzucamy parametry
    this.cartItems.forEach(item => {
      body = body.append('partId', item.partId.toString());
      body = body.append('quantity', item.quantity.toString());
    });

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    this.http.post<void>(
      this.ordersUrl,
      body.toString(),
      { headers }
    ).subscribe({
      next: () => {
        alert('Zamówienie złożone pomyślnie!');
        this.clearCart();
      },
      error: err => {
        console.error('Błąd przy składaniu zamówienia:', err);
        alert('Nie udało się złożyć zamówienia.');
      }
    });
  }
}
