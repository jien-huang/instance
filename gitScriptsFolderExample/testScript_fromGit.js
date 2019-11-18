import { Selector } from 'testcafe';

fixture `test from github`
    .page `http://devexpress.github.io/testcafe/example`;

test('Test Script from Git', async t => {
    await t
        .typeText('#developer-name', 'John Smith')
        .click('#submit-button');

    const articleHeader = await Selector('.result-content').find('h1');

    // Obtain the text of the article header
    let headerText = await articleHeader.innerText;
});