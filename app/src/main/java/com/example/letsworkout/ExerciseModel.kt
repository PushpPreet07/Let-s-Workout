package com.example.letsworkout

class ExerciseModel (
    private var id: Int,
    private var quote: String,
    private var name: String,
    private var image: Int,
    private var isCompleted: Boolean,
    private var isSelected: Boolean
        ) {
    fun getId() : Int{
        return id
    }

    fun setId(id: Int){
        this.id =id
    }

    fun getQuote(): String{
        return quote
    }
    fun setQuote(quote: String){
        this.quote = quote
    }
    fun getName() : String{
        return name
    }
    fun setName(name: String){
        this.name= name
    }

    fun getImage() : Int{
        return image
    }
    fun setImage(image: Int){
        this.image = image
    }
     fun getIsCompleted() : Boolean{
         return isCompleted
     }

    fun setIsCompleted(isCompleted: Boolean){
        this.isCompleted = isCompleted
    }

    fun getIsSelected() : Boolean{
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean){
        this.isSelected = isSelected
    }

}