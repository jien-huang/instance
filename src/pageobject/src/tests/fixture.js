import TestcafeExample_Page from './TestcafeExample_Page';

fixture `My fixture`
    .page `https://devexpress.github.io/testcafe/example/`;

      
test('TestCafe Example', async _ => {

    var ids = [
        { id: 'tried-test-cafe', value: 'on' },
        { id: 'background-parallel-testing', value: 'on' },
        { id: 'developer-name', value: 'firstname lastname' },
        { id: 'remote-testing', value: 'on' }
    ]
    var _page = new TestcafeExample_Page()
    await _page.setData(ids);

    var objs = {'CI':'on', 'windows': 'on'}
    await _page.setData(objs);
    
    await _page.click(_page.CLICKABLE.POPULATE);
    await _page.click(_page.CLICKABLE.SUBMIT_BUTTON);

});
