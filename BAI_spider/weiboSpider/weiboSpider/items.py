# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class WeibospiderItem(scrapy.Item):
    name = scrapy.Field()
    img = scrapy.Field()
    fans = scrapy.Field()
    link = scrapy.Field()
    blog = scrapy.Field()
    blogtype = scrapy.Field()
