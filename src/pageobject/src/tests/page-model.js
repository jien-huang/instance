import { Selector, t } from 'testcafe';

export default class Page {
  constructor() {
    this.url = "about:blank";
    this.data = [];
    this.id = "00000000";
    this.name = "page-model.js";
  } 

  async click(clickable_id) {
    await this.action(clickable_id, null, true);
  }

  async setBool(boolField, value = true) {
    await this.action(boolField, value, false)
  }

  async setData(ids) {
    if (ids === undefined){
      console.error('Should not pass undefined value to SetData')
    }
    if( Array.isArray(ids)){
      // we will asume you input is this format: [{id:'id of object', value: 'value you want to input'}, ...]
      for (var i = 0; i < ids.length; i++) {
        var obj = ids[i];
        await this.action(obj.id, obj.value)
      }
      return this;
    }
    if (!!ids && ids.constructor === Object) {
      // we will asume you input is this format: {id:value}
      for (var p in ids) {
        await this.action(p, ids[p])
      }
    }    
    return this;
  }

  async action(id, value, justClick = false) {
    var testObject = this.getTestObjectByIdOrName(id)
    if(!testObject) {
      console.error('we cannot find object with this id or name: [' + id +']');
      return;
    }
    var selector = Selector(this.getSelctorFromTestObject(testObject, id))
    if (justClick) {
      await t.setNativeDialogHandler(() => true).click(selector);
    } else {
      switch (String(testObject.type)){
        case 'text': await t.typeText(selector, value); break;
        case 'radio': await t.click(selector); break;
        case 'checkbox': await t.click(selector) ; break;
        case 'select-one': await t.selectText(selector, 1, 1); break;
      }
    }     
  }

  getSelctorFromTestObject(obj, nameOrId) {
    var search_str = '';    
      search_str = obj.tagName.toLowerCase() + '[name=' + obj.name + ']';
      if (obj.id === nameOrId) {
        search_str = obj.tagName.toLowerCase() + '[id=' + obj.id + ']';
      }
      return search_str;    
  }

  getTestObjectByIdOrName(nameOrId) {
    for(var i = 0; i< this.data.length; i++ ) {
      var obj = this.data[i];
      if (obj.id === nameOrId || obj.name === nameOrId) {
        return obj;
      }
    }
    return undefined;
  }
}