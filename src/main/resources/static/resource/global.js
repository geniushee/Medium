
// a태그를 Post방식으로 보내기 위한 javascrip 코드
$(function () {
    $('select[value]').each(function (index, el) {
        const value = $(el).attr('value');
        if (value) $(el).val(value);
    });

    $('a[method="post"], a[method="POST"]').click(function (e) {
        const action = $(this).attr('href');
        const csfTokenValue = $("meta[name='_csrf']").attr("content");

        // 동적으로 폼을 만든다.
        const $form = $(`<form action="${action}" method="POST"><input type="hidden" name="_csrf" value="${csfTokenValue}"></form>`);
        $('body').append($form);
        $form.submit();

        return false;
    });
});

<!-- URL에서 파라미터를 받아서 객체로 파싱-->s
function getQueryParams(){
    const params = new URLSearchParams(window.location.search)
    const paramsObj = {};

    for (const [key, value] of params){
        paramsObj [key] = value;
    }

    return paramsObj;
}