provider "aws" {
  region = "us-east-1"

}

resource "aws_s3_bucket" "s3-bucket" {
  bucket = var.bucket_name
}

resource "aws_s3_bucket_versioning" "s3-bucket-versioning" {
  bucket = aws_s3_bucket.s3-bucket.id
  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_s3_bucket_policy" "bucket_policy" {
  bucket = aws_s3_bucket.s3-bucket.id
  policy = var.bucket_policy
}

resource "aws_s3_bucket_accelerate_configuration" "s3-acceleration" {
  bucket = aws_s3_bucket.s3-bucket.id
  status = "Enabled"
}

resource "aws_s3_bucket_public_access_block" "public-access-block" {
  bucket = aws_s3_bucket.s3-bucket.id
  block_public_acls = false
  block_public_policy = true
  ignore_public_acls = true
  restrict_public_buckets = true
}


