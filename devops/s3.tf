# Configure the AWS Provider
provider "aws" {
  region = "us-east-1"  # Replace with your desired AWS region
}

# Create an S3 Bucket
resource "aws_s3_bucket" "my_bucket" {
  bucket = "my-unique-bucket-name"  # Replace with your desired bucket name
  acl    = "private"                # Set the bucket's access control list

  # Enable versioning
  versioning {
    enabled = true
  }

  # Enable server-side encryption by default
  server_side_encryption_configuration {
    rule {
      apply_server_side_encryption_by_default {
        sse_algorithm = "AES256"
      }
    }
  }

  # Optional: Tags for the bucket
  tags = {
    Name        = "MyS3Bucket"
    Environment = "Dev"
  }
}

# Optional: Outputs to display bucket information after creation
output "bucket_name" {
  value = aws_s3_bucket.my_bucket.bucket
}

output "bucket_arn" {
  value = aws_s3_bucket.my_bucket.arn
}
