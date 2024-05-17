const perfilBox = document.querySelector('.navbar_right .perfil_user');
const optionsBox = perfilBox.querySelector('.user_options');

perfilBox.addEventListener('click', () => {
    optionsBox.style.display = optionsBox.style.display === 'block' ? 'none' : 'block';
})