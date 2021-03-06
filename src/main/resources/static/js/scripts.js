/***************** Waypoints ******************/

$(document).ready(function () {

    $('.wp1').waypoint(function () {
        $('.wp1').addClass('animated fadeInLeft');
    }, {
        offset: '75%'
    });
    $('.wp2').waypoint(function () {
        $('.wp2').addClass('animated fadeInUp');
    }, {
        offset: '75%'
    });
    $('.wp3').waypoint(function () {
        $('.wp3').addClass('animated fadeInDown');
    }, {
        offset: '55%'
    });
    $('.wp4').waypoint(function () {
        $('.wp4').addClass('animated fadeInDown');
    }, {
        offset: '75%'
    });
    $('.wp5').waypoint(function () {
        $('.wp5').addClass('animated fadeInUp');
    }, {
        offset: '75%'
    });
    $('.wp6').waypoint(function () {
        $('.wp6').addClass('animated fadeInDown');
    }, {
        offset: '75%'
    });

});

/***************** Slide-In Nav ******************/

$(window).load(function () {

    $('.nav_slide_button').click(function () {
        $('.pull').slideToggle();
    });

});

/***************** Smooth Scrolling ******************/

$(function () {

    $('a[href*=#]:not([href=#])').click(function () {
        if (location.pathname.replace(/^\//, '') === this.pathname.replace(/^\//, '') && location.hostname === this.hostname) {

            var target = $(this.hash);
            target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
            if (target.length) {
                $('html,body').animate({
                    scrollTop: target.offset().top
                }, 2000);
                return false;
            }
        }
    });

});

/***************** Nav Transformicon ******************/

document.querySelector("#nav-toggle").addEventListener("click", function () {
    this.classList.toggle("active");
});

/***************** Overlays ******************/

$(document).ready(function () {
    if (Modernizr.touch) {
        // show the close overlay button
        $(".close-overlay").removeClass("hidden");
        // handle the adding of hover class when clicked
        $(".img").click(function (e) {
            if (!$(this).hasClass("hover")) {
                $(this).addClass("hover");
            }
        });
        // handle the closing of the overlay
        $(".close-overlay").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            if ($(this).closest(".img").hasClass("hover")) {
                $(this).closest(".img").removeClass("hover");
            }
        });
    } else {
        // handle the mouseenter functionality
        $(".img").mouseenter(function () {
            $(this).addClass("hover");
        })
            // handle the mouseleave functionality
            .mouseleave(function () {
                $(this).removeClass("hover");
            });
    }
});

/***************** Flexsliders ******************/

$(window).load(function () {

    $('#portfolioSlider').flexslider({
        animation: "slide",
        directionNav: false,
        controlNav: true,
        touch: false,
        pauseOnHover: true,
        start: function () {
            $.waypoints('refresh');
        }
    });

    $('#servicesSlider').flexslider({
        animation: "slide",
        directionNav: false,
        controlNav: true,
        touch: true,
        pauseOnHover: true,
        start: function () {
            $.waypoints('refresh');
        }
    });

    $('#teamSlider').flexslider({
        animation: "slide",
        directionNav: false,
        controlNav: true,
        touch: true,
        pauseOnHover: true,
        start: function () {
            $.waypoints('refresh');
        }
    });

});

//For the datalist

function setCityData() {
    let data = document.getElementById("city-input").value;
    if (data.length == 1) {
        document.getElementById("city-input").setAttribute('list', 'city');    //This will add attribute to the input
    }
    if (data.length == 0) {
        document.getElementById("city-input").removeAttribute('list');         //This will remove the attribute from the input
    }
}

function setGroupData() {
    let data = document.getElementById("group-input").value;
    if (data.length == 1) {
        document.getElementById("group-input").setAttribute('list', 'group');    //This will add attribute to the input
    }
    if (data.length == 0) {
        document.getElementById("group-input").removeAttribute('list');         //This will remove the attribute from the input
    }
}

function setDeleteGroupData() {
    let data = document.getElementById("delete-group-input").value;
    if (data.length == 1) {
        document.getElementById("delete-group-input").setAttribute('list', 'delete-group');    //This will add attribute to the input
    }
    if (data.length == 0) {
        document.getElementById("delete-group-input").removeAttribute('list');         //This will remove the attribute from the input
    }
}

function setInstitutionData() {
    let data = document.getElementById("institution-input").value;
    if (data.length == 1) {
        document.getElementById("institution-input").setAttribute('list', 'institution');    //This will add attribute to the input
    }
    if (data.length == 0) {
        document.getElementById("institution-input").removeAttribute('list');         //This will remove the attribute from the input
    }
}

function setPadding(checkCity, checkInstitution, findInstitutions, checkUniversitySchedule) {
    if (checkCity === false || checkInstitution === false) {
        document.getElementById('hero').setAttribute('style', 'padding: 0 0 195px 0 ');
    } else {
        if (findInstitutions === true && checkUniversitySchedule === false) {
            document.getElementById('hero').setAttribute('style', 'padding: 0 0 307px 0');
        } else {
            document.getElementById('hero').setAttribute('style', 'padding: 0 0 214px 0');
        }
    }
}

function setProfilePadding(groups, error) {
    if (groups === 0) {
        document.getElementById('hero').setAttribute('style', 'padding: 0 0 258px 0 ');
    } else {
        if (error === true) {
            document.getElementById('hero').setAttribute('style', 'padding: 0 0 78px 0');
        } else {
            document.getElementById('hero').setAttribute('style', 'padding: 0 0 180px 0');
        }
    }
}

