// Countries
var country_arr = new Array("Medicine1", "Medicine2", "Medicine3", "Medicine4", "Medicine5", "Medicine6");

// States
var s_a = new Array();
s_a[0] = "";
s_a[1] = "30";
s_a[2] = "40";
s_a[3] = "50";
s_a[4] = "60";
s_a[5] = "70";
s_a[6] = "80";

function populateStates(countryElementId, stateElementId) {

    var selectedCountryIndex = document.getElementById(countryElementId).selectedIndex;

    var stateElement = document.getElementById(stateElementId);

    stateElement.length = 0; 
    stateElement.selectedIndex = 0;

    var state_arr = s_a[selectedCountryIndex].split("|");

    for (var i = 0; i < state_arr.length; i++) {
        stateElement.options[stateElement.length] = new Option(state_arr[i], state_arr[i]);
    }
}

function populateCountries(countryElementId, stateElementId) {

    var countryElement = document.getElementById(countryElementId);
    countryElement.length = 0;
    countryElement.options[0] = new Option('Select Medicine', '-1');
    countryElement.selectedIndex = 0;
    for (var i = 0; i < country_arr.length; i++) {
        countryElement.options[countryElement.length] = new Option(country_arr[i], country_arr[i]);
    }

    // Assigned all countries. Now assign event listener for the states.

    if (stateElementId) {
        countryElement.onchange = function () {
            populateStates(countryElementId, stateElementId);
        };
    }
}

var checkboxname= 'create';
var labelname= 'lable';
var inputname= 'time';
	
    function populate(slct1, slct2) {
        var s1 = document.getElementById(slct1);
        var s2 = document.getElementById(slct2);
        var optionArray=0;
        s2.innerHTML = "";
        if (s1.value == "1") {
            optionArray = ["one"];
        } else if (s1.value == "2") {
            optionArray = ["one", "two"];
        } else if (s1.value == "3") {
            optionArray = ["one", "two", "three"];
    } else if (s1.value == "4") {
            optionArray = ["one", "two", "three", "four"];
    } else if (s1.value == "5") {
            optionArray = ["one", "two", "three", "four", "five"];
    } else if (s1.value == "6") {
            optionArray = ["one", "two", "three", "four", "five", "six"];
    }

var i=1;
    for (var option in optionArray) {
	
        if (optionArray.hasOwnProperty(option)) {
            var pair = optionArray[option];
            var checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.name = pair;
			checkbox.id= checkboxname + i;
			checkbox.setAttribute("onclick","clickevent(this.id);");
            checkbox.value = pair;
            s2.appendChild(checkbox);
    
            var label = document.createElement('label');
            
            label.htmlFor = pair;
			label.id= labelname + i;
						i++;
            label.appendChild(document.createTextNode(pair));

            s2.appendChild(label);
		
        }
    }
}

function clickevent(s2)
{
var n=s2.substring(checkboxname.length);

var lblname= labelname+ n;
var inputcheck= inputname + n;
if(!document.getElementById(inputcheck))
{

var abc= document.getElementById(lblname).appendChild(document.createElement("input"));
abc.id=inputname + n;

var date = new Date();
var hour = date.getHours();
var minute = date.getMinutes();
var seconds = date.getSeconds();

document.getElementById(inputname+n).value= hour+ ":"+minute+ ":"+seconds;
document.getElementById(inputname+n).disabled = true;
document.getElementById(inputname+n).style.width= "90px";
document.getElementById(inputname+n).style.fontSize= "xx-small";
}
}
