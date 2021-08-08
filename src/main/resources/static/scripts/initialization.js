$(document).ready(function(){
    $('select').formSelect();
    $('.tooltipped').tooltip();
    $('.collapsible').collapsible();
    new WOW().init();
    new ClipboardJS('.clipboardBtn');
});