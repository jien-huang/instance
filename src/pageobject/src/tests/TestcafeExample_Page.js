import Page from './page-model'
export default class TestcafeExample_Page extends Page {

  constructor() {
    super();
    this.data = 
[
  {
    "id": "developer-name",
    "name": "name",
    "tagName": "INPUT",
    "type": "text"
  },
  {
    "id": "populate",
    "tagName": "INPUT",
    "type": "button",
    "value": "Populate"
  },
  {
    "id": "remote-testing",
    "name": "remote",
    "tagName": "INPUT",
    "type": "checkbox",
    "value": "on"
  },
  {
    "id": "reusing-js-code",
    "name": "re-using",
    "tagName": "INPUT",
    "type": "checkbox",
    "value": "on"
  },
  {
    "id": "background-parallel-testing",
    "name": "background",
    "tagName": "INPUT",
    "type": "checkbox",
    "value": "on"
  },
  {
    "id": "continuous-integration-embedding",
    "name": "CI",
    "tagName": "INPUT",
    "type": "checkbox",
    "value": "on"
  },
  {
    "id": "traffic-markup-analysis",
    "name": "analysis",
    "tagName": "INPUT",
    "type": "checkbox",
    "value": "on"
  },
  {
    "id": "windows",
    "name": "os",
    "tagName": "INPUT",
    "type": "radio",
    "value": "Windows"
  },
  {
    "id": "macos",
    "name": "os",
    "tagName": "INPUT",
    "type": "radio",
    "value": "MacOS"
  },
  {
    "id": "linux",
    "name": "os",
    "tagName": "INPUT",
    "type": "radio",
    "value": "Linux"
  },
  {
    "id": "preferred-interface",
    "name": "preferred-interface",
    "tagName": "SELECT",
    "type": "select-one",
    "value": "Command Line"
  },
  {
    "id": "tried-test-cafe",
    "name": "tried-test-cafe",
    "tagName": "INPUT",
    "type": "checkbox",
    "value": "on"
  },
  {
    "id": "submit-button",
    "tagName": "BUTTON",
    "type": "submit"
  },
  {
    "id": "testcafe-rank",
    "name": "testcafe-rank",
    "tagName": "INPUT",
    "type": "hidden",
    "value": "1"
  }
];
this.id = '4d4bc1c0';
this.name = 'TestcafeExample_Page.js';
this.url = '/testcafe/example/';
this.CLICKABLE = {
  "POPULATE": "populate",
  "SUBMIT_BUTTON": "submit-button"
};
this.BOOLFIELD = {
  "REMOTE_TESTING": "remote-testing",
  "REUSING_JS_CODE": "reusing-js-code",
  "BACKGROUND_PARALLEL_TESTING": "background-parallel-testing",
  "CONTINUOUS_INTEGRATION_EMBEDDING": "continuous-integration-embedding",
  "TRAFFIC_MARKUP_ANALYSIS": "traffic-markup-analysis",
  "WINDOWS": "windows",
  "MACOS": "macos",
  "LINUX": "linux",
  "TRIED_TEST_CAFE": "tried-test-cafe"
};
this.TEXTFIELD = {
  "DEVELOPER_NAME": "developer-name"
};
this.TEXTFIELD = {
  "PREFERRED_INTERFACE": "preferred-interface"
};

    console.log('Perform on Page:' + this.url + ' Id:' + this.id + ' Page Object:' + this.name);
  }
}
