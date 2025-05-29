<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ALEXEY MARKET | Товары со всей России</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(135deg, #6e8efb, #a777e3);
            color: white;
            padding: 3rem 0;
            margin-bottom: 2rem;
        }
        .product-card {
            transition: transform 0.3s;
            margin-bottom: 1.5rem;
        }
        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        .search-box {
            background-color: #f8f9fa;
            padding: 2rem;
            border-radius: 10px;
            margin-bottom: 2rem;
        }
        .create-form {
            background-color: #f8f9fa;
            padding: 2rem;
            border-radius: 10px;
        }
        .footer {
            background-color: #343a40;
            color: white;
            padding: 2rem 0;
            margin-top: 3rem;
        }
    </style>
</head>
<body>
<!-- Шапка сайта -->
<header class="hero-section text-center">
    <div class="container">
        <h1 class="display-4 fw-bold">ALEXEY MARKET</h1>
        <p class="lead">Товары со всей России</p>
    </div>
</header>

<div class="container">
    <!-- Поисковая строка -->
    <div class="search-box">
        <form action="/" method="get" class="row g-3">
            <div class="col-md-8">
                <input type="text" name="title" class="form-control form-control-lg" placeholder="Поиск по названию объявления...">
            </div>
            <div class="col-md-4">
                <button type="submit" class="btn btn-primary btn-lg w-100">Найти товары</button>
            </div>
        </form>
    </div>

    <!-- Список товаров -->
    <div class="row">
        <#list products as product>
            <div class="col-md-4">
                <div class="card product-card h-100">
                    <#if product.images?has_content>
                        <img src="/images/${product.images[0].id}" class="card-img-top" alt="${product.title}" style="height: 200px; object-fit: cover;">
                    <#else>
                        <div class="bg-secondary text-white text-center p-5" style="height: 200px;">
                            Нет изображения
                        </div>
                    </#if>
                    <div class="card-body">
                        <h5 class="card-title">${product.title}</h5>
                        <p class="card-text text-muted">${product.city}</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="h4 text-primary">${product.price} руб.</span>
                            <a href="/product/${product.id}" class="btn btn-outline-primary">Подробнее</a>
                        </div>
                    </div>
                </div>
            </div>
        <#else>
            <div class="col-12">
                <div class="alert alert-info text-center">
                    <h3>Товаров не найдено</h3>
                    <p>Попробуйте изменить параметры поиска или добавьте новый товар</p>
                </div>
            </div>
        </#list>
    </div>

    <!-- Форма добавления товара -->
    <div class="create-form mt-5">
        <h2 class="mb-4">Добавить новый товар</h2>
        <form action="/product/create" method="post" enctype="multipart/form-data">
            <div class="row g-3">
                <div class="col-md-6">
                    <label class="form-label">Название объявления</label>
                    <input type="text" name="title" class="form-control" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Цена (руб.)</label>
                    <input type="number" name="price" class="form-control" required>
                </div>
                <div class="col-12">
                    <label class="form-label">Описание</label>
                    <textarea name="description" class="form-control" rows="3"></textarea>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Город</label>
                    <input type="text" name="city" class="form-control" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Ваше имя</label>
                    <input type="text" name="author" class="form-control" required>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Фотография 1</label>
                    <input type="file" name="file1" class="form-control" accept="image/*">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Фотография 2</label>
                    <input type="file" name="file2" class="form-control" accept="image/*">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Фотография 3</label>
                    <input type="file" name="file3" class="form-control" accept="image/*">
                </div>
                <div class="col-12 mt-3">
                    <button type="submit" class="btn btn-success btn-lg">Добавить товар</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Подвал сайта -->
<footer class="footer text-center">
    <div class="container">
        <p class="mb-0">© 2023 ALEXEY MARKET. Все права защищены.</p>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>