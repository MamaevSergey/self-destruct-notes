/* Carousel functionality */
document.addEventListener('DOMContentLoaded', function() {
    const carousel = document.querySelector('.carousel');
    const slides = document.querySelectorAll('.carousel-slide');
    const indicators = document.querySelectorAll('.indicator');
    let currentSlide = 0;

    // Функция для переключения на определённый слайд
    function goToSlide(slideIndex) {
        // Удаляем активный класс у всех слайдов и индикаторов
        slides.forEach(slide => slide.classList.remove('active'));
        indicators.forEach(indicator => indicator.classList.remove('active'));

        // Добавляем активный класс к текущему слайду и индикатору
        slides[slideIndex].classList.add('active');
        indicators[slideIndex].classList.add('active');

        currentSlide = slideIndex;
    }

    // Добавляем слушатели на каждый индикатор
    indicators.forEach((indicator, index) => {
        indicator.addEventListener('click', function() {
            goToSlide(index);
        });
    });

    // Инициализация - показываем первый слайд
    goToSlide(0);

    // Автоматическое переключение слайдов каждые 4 секунды с плавным затуханием
    setInterval(function() {
        const nextSlide = (currentSlide + 1) % slides.length;
        goToSlide(nextSlide);
    }, 8000);
});
