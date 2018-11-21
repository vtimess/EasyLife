$(document).ready(
    function() {
        $(".ulTitle").click(
            function() {
                var index = $(this).index();
                // console.log(index);
                $(this).addClass("ulTitle_active").siblings()
                    .removeClass("ulTitle_active");
                for (var i = 0; i < 4; i++)
                    if (i == index / 2) {
                        $(".ulTitle+ul").eq(i).show();
                        $(".ulTitle+ul li").eq(0).addClass("active")
                            .siblings().removeClass("active");
                    } else
                        $(".ulTitle+ul").eq(i).hide();
            });

        $("aside ul li").click(function() {
            var index = $(this).index();
            // console.log(index);
            $(this).addClass("active").siblings().removeClass("active");
        })
    })

function show(name) {
    var id = "#" + name;
    $(".content").hide();
    $(id).show();
    showIframe(name);
}

function showIframe(name) {
    var path = "other/" + name + ".html";
    // console.log(path);
    $("#iframe").attr('src', path);
}