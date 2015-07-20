// FUNCTIONS
(function($) {
    var _oldCss = $.fn.css,
            _newCss = function(name, value) {
                return $.access(this, function(elem, name, value) {
                    var styles, len,
                            map = {},
                            i = 0;

                    if ($.isArray(name)) {
                        styles = getStyles(elem);
                        len = name.length;

                        for (; i < len; i++) {
                            map[ name[ i ] ] = $.css(elem, name[ i ], false, styles);
                        }

                        return map;
                    }

                    return value !== undefined ?
                            // Webkit detection script
                            // As there is no polyfill for CSS3 calc() specifically for webkit, this is a workaround on the issue
                            // @see http://stackoverflow.com/questions/6579901/how-to-test-for-mobile-webkit
                            // @see http://caniuse.com/calc
                            $.trim(value).indexOf('calc') === 0 ?
                            $.style(elem, name, (RegExp(" AppleWebKit/").test(navigator.userAgent) ? '-webkit-' : '') + value) :
                            $.style(elem, name, value) :
                            $.css(elem, name);
                }, name, value, arguments.length > 1);
            };
    $.fn.css = _newCss; // Duck-punch
})(jQuery);



$.fn.panelSizing = function() {
    var menu = $('.client-admin-menu').width(),
            container = $('body').width(),
            panel = $('.client-admin-panel');

    panel.width(container - menu + 'px');
}

// Clone Menu
$.fn.cloneMenu = function() {
    var original = $('.menu-container.expanded'),
            cloned = $('.menu-container.collapsed');

    original.clone().children().appendTo('.menu-container.collapsed');
    cloned.find('p').hide();
}

$.fn.collapseMenu = function() {
    var expMenu = $('.collapse-menu, .quicksearch-container, .menu-container.expanded'),
            colMenu = $('.expand-menu, .menu-container.collapsed'),
            panel = $('.client-admin-menu, .menu-bg, .expand-menu, .collapse-menu'),
            content = $('.client-admin-panel'),
            button = $('.menu-container.expanded').find('.collapse-menu'),
            container = $('.content', '#container');


    button.on('click', function() {
        $('.menu-category').show();/*  fix: on collapse hide elements  sd */
        $('.search-button').hide(); /* hide search after collapse  */
        $('.dropdown li').show();/* sd */
        $('.group1, .group2, .group3, .menu-container .h-separator').show();/* sd */

        expMenu.hide();
        colMenu.show();
        panel.animate({width: '50'}, 300);
        content.animate({width: '+=190', left: '-=190'}, 300);

        container.css({width: 'calc(100% - 80px)'});
        container.css({width: '-webkit-calc(100% - 80px)'});
        container.addClass('transition');
    });
}

$.fn.expandMenu = function() {
    var expMenu = $('.collapse-menu, .quicksearch-container, .menu-container.expanded'),
            colMenu = $('.expand-menu, .menu-container.collapsed'),
            panel = $('.client-admin-menu, .menu-bg, .expand-menu, .collapse-menu'),
            content = $('.client-admin-panel'),
            button = $('.menu-container.collapsed').find('.expand-menu'),
            container = $('.content', '#container');

    button.on('click', function() {


        colMenu.hide();
        expMenu.show();
        panel.animate({width: '240'}, 300);
        content.animate({width: '-=190', left: '+=190'}, 300);

        container.removeClass('transition');
        container.css({width: 'calc(100% - 270px)'});
        container.css({width: '-webkit-calc(100% - 270px)'});




    });
}

//Search Menu
$.fn.searchMenu = function() {
    var searchbar = $('.dockbar-search'),
            sbutton = $('.search-button-small'),
            expand = $('.menu-container.collapsed').find('.expand-menu');

    searchbar.on('keydown keypress keyup change paste', function() {
        var search = this.value;
        var $li = $('.dropdown li').hide();
        $('.menu-category').hide();/* on type action hide menu-categories sd */

        $li.filter(function() {
            return $(this).text().toLowerCase().indexOf(search) >= 0;
        }).show();
    });

    searchbar.on('keyup keydown keypress change paste', function() {
        if ($(this).val() == '') {
            $('.menu-container.expanded').removeClass('search-results');
            $('.search-button').hide();
            $('.dockbar-search').removeClass('type-search');
            $('.group1, .group2, .group3, .menu-container .h-separator').show();
        } else {
            $('.menu-container.expanded').addClass('search-results');
            $('.search-button').show();
            $('.dockbar-search').addClass('type-search');
            $('.group1, .group2, .group3, .menu-container .h-separator').hide();
        }
    });

    sbutton.on('click', function() {
        expand.trigger('click');
    })
}

//Expanded Menu Effects
$.fn.expandedEffects = function() {
    var menuCat = $('.menu-container.expanded .menu-category');

    menuCat.on('click', function() {
        $(this).next('.dropdown').slideToggle(400).toggleClass('open', 400);
        $(this).toggleClass('open', 50);
    });
}

//Collapsed Menu Effects
$.fn.collapsedEffects = function() {
    var menuCat = $('.menu-container.collapsed .menu-category');

    menuCat.on('mouseenter', function() {
        if ($(this).next('.dropdown').is(':hidden')) {
            $(this).children('span').children('p').fadeIn(100).siblings().fadeOut();
        }
    });

    menuCat.on('mouseleave', function() {
        $(this).children('span').children('p').fadeOut();
    });

    menuCat.on('click', function() {
        if ($(this).next('.dropdown').is(':hidden')) {

            $(this).next('.dropdown').fadeIn(400).siblings('.dropdown').fadeOut(50);
            $(this).children('span').children('p').fadeOut();
        } else {
            $(this).next('.dropdown').fadeOut(400);
        }
    });

    $(document).on('mouseup', function(e) {
        var dropdown = $('.menu-container.collapsed .dropdown');

        if (dropdown.has(e.target).length === 0) {
            dropdown.fadeOut(400);
        }
    });
}

// Collapse Forms
$.fn.collapseForm = function() {
    $(document).on('click', '.formheader .minimize', function() {

        $(this).parent().toggleClass('collapsed', 1000);
        $(this).parent().children(this).toggleClass('collapsed');
        $(this).parent().next().slideToggle(500);
    });
}

// Fix horizontal scroll
$.fn.fixScroll = function() {
    $(document).on('scroll', function() {
        var margin = $(window).scrollLeft();
        var panel = $('.menu-bg, .expand-menu, .collapse-menu');

        panel.css('left', '-' + margin + 'px');
    });
}

// FUNCTIONS ASSIGNMENT

$(window).load(function() {
    $.fn.panelSizing();
    $.fn.cloneMenu();
    $.fn.collapseMenu();
    $.fn.expandMenu();
    $.fn.searchMenu();
    $.fn.expandedEffects();
    $.fn.collapsedEffects();
    $.fn.collapseForm();
    $.fn.fixScroll();
});

$(window).resize(function() {
    $.fn.panelSizing();
});
