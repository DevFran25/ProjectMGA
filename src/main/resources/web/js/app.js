( () => {

    const eyeIcon = document.querySelector('#eyeIcon');
    const inputPass = document.querySelector('.form_group-pass input');
    const paginacionIcons = document.querySelectorAll('.pagination ul li a');

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const paramValue = urlParams.get("page") ?? 1;

    let iconActive = null;

    paginacionIcons.forEach( icon => {
        if( icon.textContent == paramValue ){
            iconActive = icon;
        }
    })
    if( iconActive != null ){
        iconActive.classList.add('active');
    }

    if( eyeIcon != null ){
        eyeIcon.addEventListener('click', () => {
            console.log( inputPass.type == "password" );
            inputPass.type = inputPass.type == "password" ? "text" : "password";
        })
    }

    const projectsContainer = document.querySelector('#projectsContainer');
    const type = document.querySelectorAll(".type");

    type.forEach( btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const elem = e.currentTarget;
            if(elem.id == "line" && !projectsContainer.classList.contains("line") ){
                projectsContainer.classList.remove("grid");
                projectsContainer.classList.add("line");
                addClass(elem);
            }else{
                projectsContainer.classList.remove("line");
                projectsContainer.classList.add("grid");
                addClass(elem);
            }
        })
    })

    function addClass(elem){
        document.querySelector('.type.active').classList.remove("active");
        elem.classList.add("active");
    }



})()