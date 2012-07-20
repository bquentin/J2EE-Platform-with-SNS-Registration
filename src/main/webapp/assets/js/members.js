$(document).ready(function() {

    $(".profile_preview").mouseenter(function(e) {

        l_pos = $("#image_" + this.getAttribute("data_preview")).offset().left
        t_pos = $("#image_" + this.getAttribute("data_preview")).offset().top

        display_preview($("#content_" + this.getAttribute("data_preview")).html(), l_pos, t_pos);
    })

    $(".profile_preview").mouseleave(function(e) {
        $("#customer_profile").css({visibility: "hidden"})
    })

    function display_preview(content, left, top) {

        $("#customer_profile").html(content)
        $("#customer_profile").css({visibility: "visible"})

        $("#customer_profile").css({ left:l_pos, top:t_pos + 100 });
        $("#customer_profile").addClass("top");

        if (isPreviewIntoView()) {
            $("#customer_profile").css({ left:l_pos, top:t_pos - 100 });
            $("#customer_profile").removeClass("top");
        }
    }

    function isPreviewIntoView() {
        var docViewTop = $(window).scrollTop();
        var docViewBottom = docViewTop + $(window).height();
        var elemTop = $("#customer_profile").offset().top - 100;
        var elemBottom = elemTop + $("#customer_profile").height();
        return ((elemBottom <= docViewBottom) && (elemTop >= docViewTop));
    }
});