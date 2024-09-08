document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('view-terms-modal');
    const openModalButton = document.getElementById('openTermsModal');
    const closeModalButtons = document.querySelectorAll('[data-modal-hide="view-terms-modal"]');

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
