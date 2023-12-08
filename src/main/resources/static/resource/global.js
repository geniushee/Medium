
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