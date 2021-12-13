document.getElementById("get").addEventListener("click", captureFunction);
var classname = document.getElementById("classname");

function captureFunction() {
    var _name = classname.value.trim()

    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.sendMessage(tabs[0].id, {type:"get", name: _name}, function(response){
        if(!response) {
            alert('We got nothing from your browser, please check.')
        }
//        console.log(tabs[0].url.toLowerCase())

        var input = document.getElementById('code')
        input.value = response.content
        console.log(response)
        classname.value = response.name
        input.focus();
        input.select();
        document.execCommand('Copy');
        classname.focus();
        });
    });
}
