# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class PricespiderItem(scrapy.Item):
    name = scrapy.Field()
    price = scrapy.Field()
    link = scrapy.Field()
    platform = scrapy.Field()
    image = scrapy.Field()
    point = scrapy.Field()
    sales = scrapy.Field()
