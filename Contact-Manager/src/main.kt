import java.io.File
import java.util.Scanner

/**
 * Project Description: Develop a CLI contact manager that allows users to add, view, edit, search,
 * and delete contacts.
 *
 * Date: August 7th,2025
 *
 * Features:
 * - Add new contact with fields: name, phone #, email
 * - List all contacts sorted alphabetically
 * - Search contacts by name
 * - Update contact details
 * - Delete contacts
 * - Save and load contacts between program runs
 *
 * Project Status: Complete
 */
const val fileName = "contactlist.txt"
const val delimiter = ","

/**
 * Purpose: Display the Menu options
 */
fun displayMenu() {
    println("""
        -------------
        Contact Manager 
        
        1. Add Contact
        2. Update Contact
        3. Delete Contact
        4. Search Contact
        5. Display Contacts
        6. Exit
        -------------
    """.trimIndent())
}

/**
 * Purpose: Writes contacts into file
 */
fun saveContacts(contacts: List<Contact>){
   File(fileName).writeText(contacts.joinToString("\n")
   {"${it.name}$delimiter${it.phone}$delimiter${it.email}"})
}

/**
 * Purpose: Loads all the contacts from file
 */
fun loadContacts(): MutableList<Contact>{
    val file = File(fileName)
    if (!file.exists()) return mutableListOf()
    return file.readLines()
        .mapNotNull { line ->
            val parts = line.split(delimiter)
            if (parts.size == 3) Contact(parts[0].trim(), parts[1].trim(),
                parts[2].trim()) else null
        }.toMutableList()
}

fun isDuplicateContact(name: String, phone: String): Boolean {
    return loadContacts().any {
        it.name.equals(name,true) && it.phone == phone }
}


/**
 * Purpose: Add a new contact to text file
 */
fun create(){

    while(true) {
        //get user input
        print("Enter in a name: ")
        val name = readln().trim()
        print("Enter in a phone number(e.g 123-456-7890): ")
        val phone = readln().trim()
        print("Enter in an email: ")
        val email = readln().trim()

        if (isDuplicateContact(name, phone)) {
            println("=======\n[!] A contact with the same name and phone number already exists, please try again.\n=======")
            continue
        }

        //Load the Contacts
        val contacts = loadContacts()
        contacts.add(Contact(name,phone,email))
        saveContacts(contacts)
        println("[+] New contact added.")
        break
    }
}

/**
 * Purpose: Find the contact name and phone update all contact details
 */
fun update(){

    val contacts = loadContacts()
    if (contacts.isEmpty()){
        println("=======\nYou don't have any contacts yet. Create one to see it listed here.\n=======")
        return
    }

    print("Enter in the contact name: ")
    val oldName = readln().trim()
    print("Enter in the contact phone number: ")
    val oldPhone = readln().trim()
    val contact = contacts.find{it.name.equals(oldName,true) &&
            it.phone == oldPhone}

    if (contact == null){
        println("=======\n[!] Contact doesn't exist\n=======")
    }

    else {
        //Get the new contact details
        print("New name (press Enter to keep current): ")
        val newName = readln().trim().ifBlank { null }

        print("New phone number (press Enter to keep current): ")
        val newPhone = readln().trim().ifBlank { null }

        print("New email (press Enter to keep current): ")
        val newEmail = readln().trim().ifBlank { null }

        try {
            newName?.let { contact?.updateName(it) }
            newEmail?.let { contact?.updateEmail(it) }
            newPhone?.let { contact?.updatePhone(it) }

            saveContacts(contacts)
            println("[+] Contact updated successfully. ")
        } catch (e: IllegalArgumentException) {
            println("=======\n[!] Update failed: ${e.message}\n=======")
        }
    }

}

/**
 * Purpose: Find the contact name and remove it from text file
 */
fun delete() {

    val contacts = loadContacts()

    print("Enter in the name to remove: ")
    val oldName = readln().trim()
    print("Enter in the phone number to remove: ")
    val oldPhone = readln().trim()


    val newContacts = contacts.filterNot { it.name.equals(oldName, true) && it.phone == oldPhone }

    if (newContacts.size == contacts.size) {
        println("=======\n[!] Contact not found.\n=======")
    } else {
        saveContacts(newContacts)
        println("[+] Contact deleted.")
    }

}

/**
 * Purpose: Display the contents from text file
 */
fun display(){

    val contacts = loadContacts()
    if (contacts.isEmpty()){
        println("=======\nYou don't have any contacts yet. Create one to see it listed here.\n=======")
        return
    }
    println("\n Your Contacts: \n===========")
    contacts.sortedBy {it.name}.forEachIndexed { index, contact ->
        println("${index+1}. ${contact.name.lowercase()}")
    }
}

/**
 * Purpose: Find the contact name and display all contact details
 */
fun search(){

    print("Enter in the name to search: ")
    val searchName = readln().trim()
    print("Enter in the phone number to search: ")
    val searchPhone = readln().trim()

    //If either or both are empty, no point on continuing
    if(searchPhone.isBlank() || searchName.isBlank()){
        println("-----\n[!] Input have been left blank, exiting.\n------")
    }

    else {
        val found = loadContacts().filter { it.name.contains(searchName, true) && it.phone.contains(searchPhone, true) }

        if (found.isNotEmpty()) {
            println("Match found: ")
            found.forEach {
                println("============\nName: ${it.name}\nPhone Number: ${it.phone}\nEmail: ${it.email}\n============")
            }
        }
        else{
            println("-----\n[!] No contact was found with name and phone number provided.\n------")
        }
    }

}


fun main(){
    val scanner = Scanner(System.`in`)
    var running = true

    while (running){
        //Display Menu
        displayMenu()
        print("\nEnter your choice(1-${MenuOption.entries.size}): ")
        val choice = scanner.nextLine().toIntOrNull()
        val selectedOption = if(choice != null && choice in 1..MenuOption.entries.size){
            MenuOption.entries[choice - 1]
        }
        else{
            println("[!] Invalid choice. Please try again.")
            continue
        }

        when(selectedOption){
            MenuOption.AddContact -> create()
            MenuOption.UpdateContact -> update()
            MenuOption.DeleteContact -> delete()
            MenuOption.SearchContact -> search()
            MenuOption.DisplayContact -> display()
            MenuOption.Exit -> {
                println("Exiting program.")
                running = false
            }
        }
    }
}