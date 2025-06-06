<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${product.title} | ALEXEY MARKET</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .product-header {
            background: linear-gradient(135deg, #6e8efb, #a777e3);
            color: white;
            padding: 2rem 0;
            margin-bottom: 2rem;
        }
        .product-gallery {
            margin-bottom: 2rem;
        }
        .gallery-img {
            border-radius: 8px;
            margin-bottom: 1rem;
            transition: transform 0.3s;
            cursor: pointer;
        }
        .gallery-img:hover {
            transform: scale(1.03);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .product-details {
            background-color: #f8f9fa;
            padding: 2rem;
            border-radius: 10px;
            margin-bottom: 2rem;
        }
        .detail-item {
            margin-bottom: 1rem;
            padding-bottom: 1rem;
            border-bottom: 1px solid #eee;
        }
        .action-buttons {
            margin-top: 2rem;
        }
    </style>
</head>
<body>
<!-- Шапка товара -->
<header class="product-header">
    <div class="container text-center">
        <h1 class="display-5 fw-bold">${product.title}</h1>
        <p class="lead">Подробная информация о товаре</p>
    </div>
</header>

<div class="container">
    <!-- Галерея изображений -->
    <div class="product-gallery">
        <div class="row">
            <#if images?size == 0>
                <div class="col-12 text-center">
                    <div class="alert alert-info">
                        <i class="fas fa-image me-2"></i> Нет изображений для этого товара
                    </div>
                </div>
            <#else>
                <#list images as img>
                    <div class="col-md-4 col-6">
                        <img src="/images/${img.id}" class="img-fluid gallery-img" alt="Изображение товара ${product.title}">
                    </div>
                </#list>
            </#if>
        </div>
    </div>

    <!-- Детали товара -->
    <div class="product-details">
        <div class="detail-item">
            <h4><i class="fas fa-tag text-primary me-2"></i> Название товара</h4>
            <p class="fs-5">${product.title}</p>
        </div>

        <div class="detail-item">
            <h4><i class="fas fa-align-left text-primary me-2"></i> Описание</h4>
            <p>${product.description}</p>
        </div>

        <div class="row">
            <div class="col-md-4">
                <div class="detail-item">
                    <h4><i class="fas fa-ruble-sign text-primary me-2"></i> Цена</h4>
                    <p class="fs-4 text-success fw-bold">${product.price} руб.</p>
                </div>
            </div>

            <div class="col-md-4">
                <div class="detail-item">
                    <h4><i class="fas fa-map-marker-alt text-primary me-2"></i> Город</h4>
                    <p>${product.city}</p>
                </div>
            </div>

            <div class="col-md-4">
                <div class="detail-item">
                    <h4><i class="fas fa-user text-primary me-2"></i> Автор</h4>
                    <p>${product.author}</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Кнопки действий -->
    <div class="action-buttons text-center">
        <a href="/" class="btn btn-outline-primary me-2">
            <i class="fas fa-arrow-left me-1"></i> Вернуться к списку
        </a>
        <form action="/product/delete/${product.id}" method="post" class="d-inline">
            <button type="submit" class="btn btn-danger">
                <i class="fas fa-trash-alt me-1"></i> Удалить товар
            </button>
        </form>
    </div>
</div>

<!-- Подвал -->
<footer class="bg-dark text-white text-center py-3 mt-5">
    <div class="container">
        <p class="mb-0">© 2025 ALEXEY MARKET. Все права защищены.</p>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>