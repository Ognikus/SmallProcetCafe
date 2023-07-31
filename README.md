<h1>Проект по программированию на Java с использованием Scene Builder. Кафе с интерфейсом для админа</h1>
<p><h2>Работа выполнена с помощью графического инструмента Java javaFX, с использованием SceneBuilder и базой данных PostgreSQL (Pg Admin)</h2></p>
<p><h3>Код для создания таблиц</h3></p>

<p><h3>-- Создание таблицы Coffee</h3></p>
<p>CREATE TABLE Coffee (</p>
<p>  id SERIAL PRIMARY KEY,</p>
<p>  имя_кофе VARCHAR(255),</p>
<p>  размер_кофе VARCHAR(50),</p>
<p>  цена_кофе DECIMAL(10, 2)</p>
<p>);</p>

<p><h3>-- Создание таблицы Orders</h3></p>
<p>CREATE TABLE Orders (</p>
<p>  id SERIAL PRIMARY KEY,</p>
<p>  имя_заказа VARCHAR(255),</p>
<p>  размер_заказа VARCHAR(50),</p>
<p>  количество INT,</p>
<p>  сумма DECIMAL(10, 2),</p>
<p>  продавец VARCHAR(255)</p>
<p>);</p>
