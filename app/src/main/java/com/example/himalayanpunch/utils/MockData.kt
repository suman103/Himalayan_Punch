package com.example.himalayanpunch.utils

import com.example.himalayanpunch.model.MenuItem

// ── Mock Menu Data ─────────────────────────────────────────────────────────────

object MockData {

    val menuItems = listOf(

        // ── HOT COFFEE ────────────────────────────────────────────────────────
        MenuItem(
            itemId = "hot_001",
            name = "Himalayan Punch Espresso",
            category = "Hot Coffee",
            subCategory = "Espresso",
            price = "Rs. 280",
            priceValue = 280L,
            imageUrl = "https://images.unsplash.com/photo-1510707577719-ae7c14805e3a?w=800",
            description = "Our signature double-shot espresso crafted from single-origin Nepali high-altitude beans. Intense, bold, and perfectly balanced with notes of dark chocolate and mountain herbs. The drink that started it all.",
            rating = 4.9f,
            isFeatured = true,
            isVeg = true,
            calories = "15 kcal",
            prepTime = "3 min",
            sizes = listOf("Single", "Double", "Triple"),
            customizations = listOf("Extra Shot", "Decaf", "Oat Milk"),
            tags = listOf("Bestseller", "Signature")
        ),
        MenuItem(
            itemId = "hot_002",
            name = "Yak Butter Latte",
            category = "Hot Coffee",
            subCategory = "Latte",
            price = "Rs. 380",
            priceValue = 380L,
            imageUrl = "https://images.unsplash.com/photo-1561047029-3000c68339ca?w=800",
            description = "A Himalayan twist on the classic latte — silky espresso blended with steamed milk and a touch of traditional yak butter for a rich, nutty, warming experience. Inspired by Tibetan butter tea.",
            rating = 4.7f,
            isFeatured = true,
            isVeg = true,
            calories = "180 kcal",
            prepTime = "5 min",
            sizes = listOf("Small", "Medium", "Large"),
            customizations = listOf("Extra Shot", "Oat Milk", "Almond Milk", "Less Sugar"),
            tags = listOf("Signature", "Popular")
        ),
        MenuItem(
            itemId = "hot_003",
            name = "Sherpa's Spiced Chai",
            category = "Tea",
            subCategory = "Masala Chai",
            price = "Rs. 220",
            priceValue = 220L,
            imageUrl = "https://images.unsplash.com/photo-1567922045116-2a00fae2ed03?w=800",
            description = "A bold brew of Ilam black tea simmered with cardamom, ginger, cinnamon, and cloves. Topped with frothed whole milk. Warming, aromatic, and totally addictive — exactly how a Sherpa makes it.",
            rating = 4.8f,
            isFeatured = true,
            isVeg = true,
            calories = "120 kcal",
            prepTime = "7 min",
            sizes = listOf("Small", "Medium", "Large"),
            customizations = listOf("Less Sugar", "Extra Ginger", "Extra Spice", "Oat Milk"),
            tags = listOf("Bestseller", "Veg")
        ),

        // ── COLD COFFEE ───────────────────────────────────────────────────────
        MenuItem(
            itemId = "cold_001",
            name = "Everest Cold Brew",
            category = "Cold Coffee",
            subCategory = "Cold Brew",
            price = "Rs. 420",
            priceValue = 420L,
            imageUrl = "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=800",
            description = "Slow-steeped for 18 hours in cold mountain water, our cold brew is smooth, chocolatey, and never bitter. Served over hand-chipped ice with a splash of cream. As refreshing as a summit breeze.",
            rating = 4.8f,
            isFeatured = true,
            isVeg = true,
            calories = "60 kcal",
            prepTime = "2 min",
            sizes = listOf("Regular", "Large"),
            customizations = listOf("Sweet Cream", "Vanilla Syrup", "Oat Milk"),
            tags = listOf("Bestseller", "Cold")
        ),
        MenuItem(
            itemId = "cold_002",
            name = "Annapurna Mocha Frappe",
            category = "Cold Coffee",
            subCategory = "Frappe",
            price = "Rs. 460",
            priceValue = 460L,
            imageUrl = "https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=800",
            description = "Blended espresso, rich cocoa, whole milk, and crushed ice — topped with whipped cream and a drizzle of dark chocolate. Indulgent, thick, and absolutely worth every calorie.",
            rating = 4.6f,
            isFeatured = false,
            isVeg = true,
            calories = "380 kcal",
            prepTime = "6 min",
            sizes = listOf("Medium", "Large"),
            customizations = listOf("Extra Choco", "Less Sweet", "Oat Milk", "No Whip"),
            tags = listOf("Popular", "Cold")
        ),
        MenuItem(
            itemId = "cold_003",
            name = "Mustang Rose Iced Tea",
            category = "Cold Coffee",
            subCategory = "Iced Tea",
            price = "Rs. 320",
            priceValue = 320L,
            imageUrl = "https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=800",
            description = "Hibiscus and rose petals steeped with Nepali green tea, lightly sweetened and poured over ice. A floral, refreshing sip that looks as beautiful as it tastes.",
            rating = 4.5f,
            isFeatured = false,
            isVeg = true,
            calories = "80 kcal",
            prepTime = "4 min",
            sizes = listOf("Regular", "Large"),
            customizations = listOf("Less Sugar", "Extra Lemon"),
            tags = listOf("New", "Veg", "Cold")
        ),

        // ── FOOD ──────────────────────────────────────────────────────────────
        MenuItem(
            itemId = "food_001",
            name = "Kathmandu Club Sandwich",
            category = "Food",
            subCategory = "Sandwich",
            price = "Rs. 520",
            priceValue = 520L,
            imageUrl = "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=800",
            description = "Triple-decker toasted sandwich with grilled chicken, cheddar, lettuce, tomato, cucumber, and our house mayo on thick-cut sourdough. Served with a side of seasoned potato wedges.",
            rating = 4.7f,
            isFeatured = true,
            isVeg = false,
            calories = "650 kcal",
            prepTime = "12 min",
            sizes = listOf("Regular"),
            customizations = listOf("Veg Option", "Extra Cheese", "No Mayo"),
            tags = listOf("Bestseller", "Non-Veg")
        ),
        MenuItem(
            itemId = "food_002",
            name = "Himalayan Cheese Toast",
            category = "Food",
            subCategory = "Toast",
            price = "Rs. 320",
            priceValue = 320L,
            imageUrl = "https://images.unsplash.com/photo-1603046891726-36bfd957e0bf?w=800",
            description = "Thick sourdough slices loaded with melted local cheese, caramelised onions, and a pinch of Himalayan salt. Toasted golden and served with a small pot of herbed butter. Simple and deeply satisfying.",
            rating = 4.5f,
            isFeatured = false,
            isVeg = true,
            calories = "420 kcal",
            prepTime = "8 min",
            sizes = listOf("Regular"),
            customizations = listOf("Extra Cheese", "Add Egg", "Gluten Free"),
            tags = listOf("Veg", "Popular")
        ),
        MenuItem(
            itemId = "food_003",
            name = "Thakali Dal Bhat Platter",
            category = "Food",
            subCategory = "Meal",
            price = "Rs. 680",
            priceValue = 680L,
            imageUrl = "https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=800",
            description = "A hearty Nepali thali with steamed rice, lentil dal, seasonal vegetable curry, house-made pickle, and papad. Cooked fresh daily using traditional Thakali methods. Comfort food at its finest.",
            rating = 4.9f,
            isFeatured = true,
            isVeg = true,
            calories = "780 kcal",
            prepTime = "15 min",
            sizes = listOf("Regular", "Large"),
            customizations = listOf("Extra Dal", "Add Chicken", "Less Spicy"),
            tags = listOf("Bestseller", "Veg", "Hearty")
        )
    )

    val hotItems     get() = menuItems.filter { it.category == "Hot Coffee" || it.category == "Tea" }
    val coldItems    get() = menuItems.filter { it.category == "Cold Coffee" }
    val foodItems    get() = menuItems.filter { it.category == "Food" }
    val featured     get() = menuItems.filter { it.isFeatured }
}