<?php
/**
 * The template used to display Tag Archive pages
 *
 * @package WordPress
 * @subpackage Twenty_Eleven
 * @since Twenty Eleven 1.0
 *
 * modified by aj for nm 2012-03-27
 */

$nm_chart = strtoupper($wp_query->get('tag'));
$nm_chart_rss_feed = $nm_chart . '/feed';

get_header(); ?>

		<section id="primary">
			<div id="content" role="main">

			<header class="page-header">
<?php
// try to find a thumbnail
// this code expects installation of wordpress in a subdirectory!
$nm_thumb_path = 'thumb/' . $nm_chart . '.png';
$nm_thumb_file = ABSPATH . '../chart/' . $nm_thumb_path;
if (is_readable($nm_thumb_file)) {
	$nm_thumb_image_size = @getimagesize($nm_thumb_file);
	$nm_thumb_style = '';
	if ($nm_thumb_image_size && $nm_thumb_image_size[1]) {
		$nm_thumb_style = ' style="max-height: ' . $nm_thumb_image_size[1] / 2 . 'px;"';
	}
?>
				<div class=thumb><img src="<?php echo $nm_thumb_path; ?>"<?php echo $nm_thumb_style; ?> alt="[thumbnail]"></div>
<?php
}
?>
				<h1 class="page-title">NM Archive for Chart: <span><?php echo single_tag_title( '', false ); ?></span></h1>

				<?php
					$tag_description = tag_description();
					if ( ! empty( $tag_description ) )
						echo apply_filters( 'tag_archive_meta', '<div class="tag-archive-meta">' . $tag_description . '</div>' );
				?>
				
				<div>You can <a href="<?php echo $nm_chart_rss_feed; ?>">subscribe to the RSS feed for chart <?php echo single_tag_title( '', false ); ?></a>, which allows you to always receive the newest NM.</div>
				<hr>
			</header>

			<?php if ( have_posts() ) : ?>

				<?php twentyeleven_content_nav( 'nav-above' ); ?>

				<?php /* Start the Loop */ ?>
				<?php while ( have_posts() ) : the_post(); ?>

					<?php
						/* Include the Post-Format-specific template for the content.
						 * If you want to overload this in a child theme then include a file
						 * called content-___.php (where ___ is the Post Format name) and that will be used instead.
						 */
						get_template_part( 'content', get_post_format() );
					?>

				<?php endwhile; ?>

				<?php twentyeleven_content_nav( 'nav-below' ); ?>

			<?php else : ?>

				<article id="post-0" class="post no-results not-found">
					<header class="entry-header">
						<h2 class="entry-title"><?php _e( 'Nothing Found', 'twentyeleven' ); ?></h2>
					</header><!-- .entry-header -->

					<div class="entry-content">
						<p>Apologies, but the archives do not contain any NM for chart <?php echo single_tag_title( '', false ); ?>. This most likely means that there are no updates to this chart and that your copy of chart <?php echo single_tag_title( '', false ); ?> is still up-to-date.</p>
						<p>You can subscribe to the <a href="<?php echo $nm_chart_rss_feed; ?>">RSS feed for chart <?php echo single_tag_title( '', false ); ?></a> or to the general <a href="<?php bloginfo('rss2_url'); ?>">RSS feed for Arne’s NM</a>. This will ensure that you will receive new NM for this chart as soon as they are posted.</p>
					</div><!-- .entry-content -->
				</article><!-- #post-0 -->

			<?php endif; ?>

			</div><!-- #content -->
		</section><!-- #primary -->

<?php get_sidebar(); ?>
<?php get_footer(); ?>
