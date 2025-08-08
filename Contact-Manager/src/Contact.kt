enum class MenuOption {
    AddContact,DisplayContact,UpdateContact,DeleteContact,SearchContact,Exit
}

class Contact( private var _name:String, private var _phoneNum: String, private var _email: String) {
    val name get() = _name
    val phone get() = _phoneNum
    val email get() = _email

    init {
        updateName(_name)
        updatePhone(_phoneNum)
        updateEmail(_email)
    }

    override fun toString(): String {
        return "Name: $name,Phone: $phone,Email: $email"
    }

    fun updateName(newName:String){
        require(newName.isNotBlank()) {"Name can't be blank"}
        _name = newName
    }

    fun updatePhone(newPhone:String){
        require(Regex("^\\+?[0-9]{7,15}$").matches(newPhone)){"[!] Invalid phone number provided."}
        _phoneNum = newPhone
    }

    fun updateEmail(newEmail:String){
        require(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+$").matches(newEmail)) {"[!] Invalid email provided"}
        _email = newEmail
    }

}