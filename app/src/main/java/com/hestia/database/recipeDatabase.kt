package com.hestia.database


class recipeDatabase {
    var itemName: String? = null
        private set
    var itemIngredients: String? = null
        private set
    var itemDirections: String? = null
        private set
    var itemDescription: String? = null
        private set
    var itemDifficulty: String? = null
        private set
    var itemImage: String? = null
        private set
    var key: String? = null

    constructor() {}
    constructor(
        itemName: String?,
        itemIngredients: String?,
        itemDirections: String?,
        itemDescription: String?,
        itemDifficulty: String?,
        itemImage: String?
    ) {
        this.itemName = itemName
        this.itemIngredients = itemIngredients
        this.itemDirections = itemDirections
        this.itemDescription = itemDescription
        this.itemDifficulty = itemDifficulty
        this.itemImage = itemImage
    }
}