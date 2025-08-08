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
 */
val fileName = "contactlist.txt"

fun displayMenu(){
    println("-------------\nContact Manager\n1. Add Contact\n2. Update Contact\n" +
            "3. Delete Contact\n4. Search Contact\n5. Display Contacts\n6. Exit\n-------------")
}

fun isDuplicateContact(name: String, phone: String): Boolean {

    val file = File(fileName)
    if (!file.exists()) return false

    return file.readLines().any { line ->
        val parts = line.split("::")
        val storedName = parts.getOrNull(0)?.trim()
        val storedPhone = parts.getOrNull(1)?.trim()
        storedName.equals(name,true) && storedPhone == phone
    }
}

/**
 * Purpose: Add a new contact to text file
 */
fun create(){

    while(true) {
        //get user input
        print("Enter in a name: ")
        val name = readln().trim()
        print("Enter in a phone: ")
        val phone = readln().trim()
        print("Enter in a email: ")
        val email = readln().trim()

        if (isDuplicateContact(name, phone)) {
            println("[!] A contact with the same name and phone already exists, please try again.")
            continue
        }

        //initialize the Contact
        val contact = Contact(name, phone, email)
        File(fileName).appendText("$contact\n")
        println("[+] New contact has been added.")
        break
    }

}

/**
 * Purpose: Find the contact name and update all contact details
 */
fun update(){
    val name: String

}

/**
 * Purpose: Find the contact name and remove it from text file
 */
fun delete(){
    val name: String
}

/**
 * Purpose: Display the contents from text file
 */
fun display(){
    val file = File(fileName)
    if (!file.exists() || file.readLines().isEmpty()){
        println("=======\nYou don't have any contacts yet. Create one to see it listed here.\n=======")
        return
    }
    println("\n Your Contacts: \n===========")
    file.readLines().forEachIndexed {index , line ->
        val parts = line.split(",")
    if (parts.size == 3) println("${index+1}. ${parts[0]}")}
}

/**
 * Purpose: Find the contact name and display all contact details
 */
fun search(){
    val name: String
    val phone: String
    val email: String
}


fun main(){
    var scanner = Scanner(System.`in`)
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