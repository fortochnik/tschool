<%--
  Created by IntelliJ IDEA.
  User: mipan
  Date: 13.10.2016
  Time: 23:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel-group" id="accordion">
  <!-- 1 панель -->
  <div class="panel panel-default">
    <!-- Заголовок 1 панели -->
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">1.
          Order. Draft</a>
      </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
      <!-- Содержимое 1 панели -->

      <div class="container">
        <div class="row">
          <div class="panel-body">
            <div id="catlist">
              <dl>
                <dt><img src="http://placehold.it/300x200"
                         alt="Product image" width="93"
                         height="62"/><strong>Some info text here</strong><a
                        href="#">The Name of the Product Goes here</a></dt>
                <dd>
                  <p>The description of whatever the heading is about goes
                    here. The description of whatever the heading is
                    about goes here.The description of whatever the
                    heading is about goes here.</p>
                </dd>
              </dl>
              <dl>
                <dt><img src="http://placehold.it/300x200"
                         alt="Product image" width="93"
                         height="62"/><strong>Some info text here</strong><a
                        href="#">The Name of the Product Goes here</a></dt>
                <dd>
                  <p>The description of whatever the heading is about goes
                    here. The description of whatever the heading is
                    about goes here.The description of whatever the
                    heading is about goes here.</p>

                  <p>Another line of text to show that it doesn't wrap
                    under the image. Another line of text to show that
                    it doesn't wrap under the image. </p>
                </dd>
              </dl>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- 2 панель -->
  <div class="panel panel-default">
    <!-- Заголовок 2 панели -->
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">2.
          Order</a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse">
      <!-- Содержимое 2 панели -->
      <div class="panel-body">
        <p>Изучение технологии лучше всего начать с простого <a
                href="http://itchief.ru/lessons/bootstrap-3/20-lesson-2-getting-started-with-twitter-bootstrap-3"
                target="_blank">урока</a>. На котором изучается как подключить
          Twitter Bootstrap к своему проекту и вывести с помощью него простейший
          текст.</p>
      </div>
    </div>
  </div>
  <!-- 3 панель -->
  <div class="panel panel-default">
    <!-- Заголовок 3 панели -->
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">3.
          Order</a>
      </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse">
      <!-- Содержимое 3 панели -->
      <div class="panel-body">
        <p>Создание сайта - это трудоемкий процесс, который состоит из нескольких
          этапов. В качестве примера рассмотрим создание сайта визитки на этом <a
                  href="http://itchief.ru/lessons/bootstrap-3/website-creation-business-cards-(part-1)"
                  target="_blank">уроке</a>, в котором рассмотрим процесс загрузки
          необходимых пакетов и проектирования макета сайта.</p>
      </div>
    </div>
  </div>
</div>
