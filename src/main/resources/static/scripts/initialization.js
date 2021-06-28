$(document).ready(function(){
    $('select').formSelect();
    $('.tooltipped').tooltip();
    new WOW().init();
    new ClipboardJS('.clipboardBtn');
});