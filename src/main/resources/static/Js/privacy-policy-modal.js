document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('view-privacy-modal');
    const openModalButton = document.getElementById('openPrivacyPolicyModal');
    const closeModalButtons = document.querySelectorAll('[data-modal-hide="view-privacy-modal"]');

    openModalButton.addEventListener('click', function(event) {
        event.preventDefault();
        modal.classList.remove('hidden');
        modal.classList.add('flex');
    });

    closeModalButtons.forEach(button => {
        button.addEventListener('click', function() {
            modal.classList.remove('flex');
            modal.classList.add('hidden');
        });
    });
});
