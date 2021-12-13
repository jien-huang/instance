
chrome.runtime.onMessage.addListener(
    function (request, sender, sendResponse) {
        if (request.type === 'get') {
            sendResponse({ content: 'OK' })
            // after everything, count how many pages we got
            getPageObjects()
        }
    }
);

// use jsdoc to give end users a hint
// var clickable = {
//     SUBMIT: 'submit',
//     CANCEL: 'cancel'
// }
// /**
//  * 
//  * @param {clickable} action use clickable
//  */
// function testFunction(action) {
//     print(action)
// }
// testFunction()
/*
    consider store page objects in local storage, because sync has 102K size limit, while local is 5.2M
    all page objects will looks like below
    {
        pages: [
            {'id':'1234', 'name':'generatedPageName.js', 'captureAt':'dateTimeStamp', 'url':'https://.....', 
                'objects': [
                {'type':'button', 'text':'OK', 'id':'confirm', 'name':'confirm', 'special-id':'xxx'},
                {'type':'input', 'value':'first name', ...}
                {'type':'select', 'selected':'chosen value', options:['xxxx','xxx','xx']}
                ],
                'script': ' import .... '
            }
        ]
    }

*/

async function getPageObjects() {
    chrome.storage.sync.get('page_object', (data) => {
        // what if there is no options stored in this browser? how could this happen? maybe first time?
        options = data['page_object'];
        if (!options || options.length === 0) {
            alert('no options set in this browser, please set them.')
            return;
        }
        //now we have the options, begin to capture objects
        var onePage = {};
        onePage.url = removeRandomNumberInString(window.location.pathname);
        var str_array = onePage.url.split('/')
        var _className = ''
        for (var i = 0; i < str_array.length; i++) {
            //TODO consider add a list of can_be_ignored words here
            if (str_array[i].length === 0) {
                // remove the hostname
                continue
            }
            _className = _className + str_array[i].charAt(0).toUpperCase() + str_array[i].slice(1);
        }
        onePage.name = removeRandomNumberInString(_className).replace(/\./g, '_') + '_Page.js'
        onePage.timeStamp = new Date().toISOString();

        onePage.objects = [];
        types = options.find(item => item.name === 'types').value
        attributes = options.find(item => item.name === 'attributes').value

        items = document.querySelectorAll(types)
        console.log(items)
        for (var i = 0; i < items.length; i++) {
            var _obj = {}
            var attr_arry = attributes.split(',')
            for (var j = 0; j < attr_arry.length; j++) {
                var attr_name = attr_arry[j]
                var attr = items[i][attr_name]

                if (attr) {
                    _obj[attr_name] = attr
                }
            }
            if (_obj) {
                onePage.id += hashCode(JSON.stringify(_obj));
                onePage.objects.push(_obj);
            }
            onePage.id = hashCode(onePage.id);

        }

        // end of capture, write to storage

        // count the storage, send number to background
        chrome.storage.local.get('pages', (all_pages) => {

            if (!all_pages) {
                // nothing in the local storage now, really doubt how it happen
                console.error('How could this happen! Nothing in local storage')
                return;
            }

            if (!all_pages.pages) {
                console.log('no stored pages')
                all_pages.pages = []
            }

            pages = all_pages.pages;
            var already = false;
            pages.filter(item => {
                if (item.id === onePage.id) {
                    alert('This page has been captured. Please check.')
                    already = true;
                }
            })
            if (already) {
                return;
            }

            pages.push(onePage);
            chrome.storage.local.set({ 'pages': pages })
            count = pages.length;
            chrome.runtime.sendMessage({ 'count': count.toString() });
        })
    });
}

function removeRandomNumberInString(original_string) {
    return original_string.replace(/[0-9]+/, '')
}

function removeRandomValueInString(original_string) {
    return original_string.replace(/(\w+[_,-])+[_,-]*[0-9]+_/, '').replace(/-/g, '_').replace(/\./, '_')
}

function hashCode(e) {
    for (var r = 0, i = 0; i < e.length; i++)
        r = (r << 5) - r + e.charCodeAt(i), r &= r;
    return r.toString(16);
}
