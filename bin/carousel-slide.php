    <!-- Header Carousel -->
    <header id="myCarousel" class="carousel slide">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner">
            <div class="item active">
                <div class="fill" style="background-image:url('<?php print "http://".$_SERVER['SERVER_NAME']."/intellizone/"; ?>images/banner01.jpg');"></div>
                <div class="carousel-caption">
                    <h2>Learn Programming ...</h2>
                </div>
            </div>
            <div class="item">
                <div class="fill" style="background-image:url('<?php print "http://".$_SERVER['SERVER_NAME']."/intellizone/"; ?>images/banner02.jpg');"></div>
                <div class="carousel-caption">
                    <h2>Qualified professional trainers ...</h2>
                </div>
            </div>
            <div class="item">
                <div class="fill" style="background-image:url('<?php print "http://".$_SERVER['SERVER_NAME']."/intellizone/"; ?>images/banner03.jpg');"></div>
                <div class="carousel-caption">
                    <h2>High quality trainings ...</h2>
                </div>
            </div>
        </div>

        <!-- Controls -->
        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
            <span class="icon-prev"></span>
        </a>
        <a class="right carousel-control" href="#myCarousel" data-slide="next">
            <span class="icon-next"></span>
        </a>
    </header>