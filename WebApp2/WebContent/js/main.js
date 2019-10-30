(function() {
	  // Variables
	  var $curve = document.getElementById("curve");
	  var last_known_scroll_position = 0;
	  var defaultCurveValue = 350;
	  var curveRate = 3;
	  var ticking = false;
	  var curveValue;

	  // Handle the functionality
	  function scrollEvent(scrollPos) {
	    if (scrollPos >= 0 && scrollPos < defaultCurveValue) {
	      curveValue = defaultCurveValue - parseFloat(scrollPos / curveRate);
	      $curve.setAttribute(
	        "d",
	        "M 800 300 Q 400 " + curveValue + " 0 300 L 0 0 L 800 0 L 800 300 Z"
	      );
	    }
	  }

	  // Scroll Listener
	  // https://developer.mozilla.org/en-US/docs/Web/Events/scroll
	  window.addEventListener("scroll", function(e) {
	    last_known_scroll_position = window.scrollY;

	    if (!ticking) {
	      window.requestAnimationFrame(function() {
	        scrollEvent(last_known_scroll_position);
	        ticking = false;
	      });
	    }

	    ticking = true;
	  });
	})();

function changestatus(str, int, position) {
	  var xhttp;  
	  var elements = document.getElementsByClassName("status");
	  xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		    console.log(this.responseText);
//		    let status;
//		    if (str == "d") {
//		    	status = "Deactivated";
//		    }
//		    else {
//		    	status = "Activated";
//		    }
//		    elements[position].innerHTML = status;
		    elements[position].innerHTML = this.responseText;
		    }
		  };
	  xhttp.open("GET", "admin?status="+str+"&id="+int, true);
	  xhttp.send();
	}


function redirect() {
	  var xhttp;  
	  xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		    isRedirect = this.responseText;
			  if(isRedirect == 'invalid url registered') {
				  xhttp.onreadystatechange = function() {
					    if (this.readyState == 4 && this.status == 200) {
					    alert(this.responseText);
					    }
					  };
				  xhttp.open("GET", "redirect?redirect=true", true);
				  xhttp.send();
			  }
		    }
		  };
	  xhttp.open("GET", "redirect?redirected=true", true);
	  xhttp.send();
	}

window.onload = redirect;
var isRedirect = "";