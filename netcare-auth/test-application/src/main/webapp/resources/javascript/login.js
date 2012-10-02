function callLogin() {
	if ("true" == document.iID.GetProperty('LoggedIn')){
		alert("detected alread logged in. Logging you off!");
		document.iID.Invoke('Logout');

		if ("true" == document.iID.GetProperty('LoggedIn')){
			alert("something went wrong!");
		}else{
			alert("log out successful, trying to log you in now");
		}

	}else{
		alert("Not logged in. Logging in");
	}
	document.iID.Invoke('Login');
	alert('returned');
	alert(document.iID.GetProperty('LoggedIn'));
	

	if ("true" == document.iID.GetProperty('LoggedIn')){
		alert("wohoooo, Mission Impossible 7 accomplished");
	}else{
		alert("buhuuu, bad tommy boy!");
	}
};

function callLogin1() {
	document.iID.Invoke('Login');
	alert('returned');
	alert(document.iID.GetProperty('LoggedIn'));
};

function callLogout() {
	document.iID.Invoke('Logout');
	alert('returned');
	alert(document.iID.GetProperty('LoggedIn'));
};