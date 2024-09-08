console.log("Script loaded");

let currentTheme = getTheme();

document.addEventListener('DOMContentLoaded', () => {
	changeTheme();
});

function changeTheme(){
	//set to web page
	document.querySelector('html').classList.add(currentTheme);
	
	// set listner to change theme
	const changeThemeButton = document.querySelector('#theme_change_button');
	
	// change the text of button
	changeThemeButton.querySelector('span').textContent = (currentTheme=='light' ? 'Dark' : 'Light');
			
	changeThemeButton.addEventListener("click", (event) => {
		console.log("Change Theme button");
		const oldTheme = currentTheme;
		if(currentTheme === "dark"){
			currentTheme = "light";
		}
		else{
			currentTheme = 'dark';
		}
		
		// update in localstorage
		setTheme(currentTheme);
		document.querySelector('html').classList.remove(oldTheme);
		
		// set in web
		document.querySelector('html').classList.add(currentTheme);
		
		// change the text of button
		changeThemeButton.querySelector('span').textContent = (currentTheme=='light' ? 'Dark' : 'Light');
	});
}

//set theme to local storage
function setTheme(theme){
	localStorage.setItem("theme", theme);
}

// get theme from local storage
function getTheme(){
	let theme = localStorage.getItem("theme");
	/*
	if(theme)
		return theme;
	else
		return "light";
	*/
	return theme ? theme : "light";
}
