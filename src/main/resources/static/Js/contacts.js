console.log("modal");

const baseURL = "http://localhost:8080";

const viewContactModal = document.getElementById("view_contact_modal");

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses: 'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal(){
    contactModal.show();
}

function closeContactModal(){
    contactModal.hide();
}

async function loadContactData(contactId){
    console.log(contactId);
    
    try{
        const data = await (await fetch(`${baseURL}/api/contacts/${contactId}`)).json(); // await is used to resolve promises
        console.log(data);
        
        document.querySelector('#contact_id').value = data.contactId; // Ensure this is set correctly
        document.querySelector('#contact_name').innerHTML = data.name;
        document.querySelector('#contact_email').innerHTML = data.email;
        document.querySelector('#contact_phone').innerHTML = data.phoneNumber;
        document.querySelector('#contact_address').innerHTML = data.address;
        document.querySelector('#contact_description').innerHTML = data.description;
        document.querySelector('#contact_website').href = data.websiteLink;
        document.querySelector('#contact_website').innerHTML = data.websiteLink;
        document.querySelector('#contact_linkedin').href = data.linkedinLink;
        document.querySelector('#contact_linkedin').innerHTML = data.linkedinLink;
        document.querySelector('#contact_image').src = data.picture;
        const contactFavourite = document.querySelector('#contact_favourite');
        if(data.favourite){
            contactFavourite.innerHTML = "<i class='fa-solid fa-heart fa-2xl' style='color: #ff0000;'></i>";
        }
        openContactModal();
    }
    catch(error){
        console.log("Error: ", error);
    }
}

async function deleteUser() {
    const result = await Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    });

    if (result.isConfirmed) {
        Swal.fire({
            title: "Deleted!",
            text: "Your file has been deleted.",
            icon: "success"
        });

        const contactId = document.querySelector('#contact_id').value; // Ensure this is set correctly
        try {
            const response = await fetch(`${baseURL}/api/contacts/${contactId}`, {
                method: 'DELETE',
            });
            if (response.ok) {
                console.log('User deleted successfully');
                closeContactModal();
                // Optionally, you can refresh the contact list or remove the deleted contact from the UI
            } else {
                console.log('Failed to delete user');
            }
        } catch (error) {
            console.log('Error:', error);
        }
    }
}

// Add this function to contacts.js
function editContact() {
    const contactId = document.querySelector('#contact_id').value; // Ensure this is set correctly
    window.location.href = `${baseURL}/user/contacts/view/${contactId}`;
}

// Add event listener for the "Edit" button
document.querySelector('.edit-contact-button').addEventListener('click', editContact);

document.querySelector('.delete-user-button').addEventListener('click', deleteUser);