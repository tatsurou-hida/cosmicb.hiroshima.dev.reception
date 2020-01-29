function announceTimeout(){
//  window.open("http://localhost:8080/reception", "");
  window.location.href = "http://localhost:8080/reception";
}

setTimeout("announceTimeout()", 3000);