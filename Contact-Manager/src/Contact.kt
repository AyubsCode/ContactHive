enum class MenuOption {
    AddContact,UpdateContact,DeleteContact,SearchContact,DisplayContact,Exit
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

    fun updateName(newName: String): Boolean {
        return if (newName.isNotBlank()) {
            _name = newName
            true
        } else {
            println("Name can't be blank")
            false
        }
    }

    fun updatePhone(newPhone: String): Boolean {
        return if (Regex("^\\+?[0-9]{1,4}(-?[0-9]{2,4}){1,3}$").matches(newPhone)) {
            _phoneNum = newPhone
            true
        } else {
            println("[!] Invalid phone number format provided.")
            false
        }
    }

    fun updateEmail(newEmail: String): Boolean {
        return if (Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+$").matches(newEmail)) {
            _email = newEmail
            true
        } else {
            println("[!] Invalid email provided.")
            false
        }
    }

}