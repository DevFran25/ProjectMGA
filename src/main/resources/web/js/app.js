( () => {

    const eyeIcon = document.querySelector('#eyeIcon');
    const inputPass = document.querySelector('.form_group-pass input');

    eyeIcon.addEventListener('click', () => {
        console.log( inputPass.type == "password" );
        inputPass.type = inputPass.type == "password" ? "text" : "password";
    })
    

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