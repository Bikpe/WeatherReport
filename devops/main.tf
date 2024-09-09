terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.27.0"
    }
  }
}

module "source_s3_bucket" {
  source = "./remote_s3_bucket"
  bucket_name    = var.bucket_name
  aws_iam_role   = var.aws_iam_role
  aws_iam_policy = var.aws_iam_policy
  bucket_policy  = data.aws_iam_policy_document.bucket_policy.json
}

data "aws_iam_policy_document" "assume_role" {
  statement {
    effect = "Allow"

    principals {
      type        = "Service"
      identifiers = ["s3.amazonaws.com"]
    }

    actions = ["sts:AssumeRole"]
  }
}

data "aws_iam_policy_document" "bucket_policy" {
  statement {
    actions   = ["s3:GetObject", "s3:PutObject"]
    resources = ["arn:aws:s3:::${var.bucket_name}/*"]
  }
}

resource "aws_iam_role" "bucket_role" {
  name               = var.aws_iam_role
  assume_role_policy = data.aws_iam_policy_document.assume_role.json
}

resource "aws_iam_policy" "iam_policy" {
  name   = var.aws_iam_policy
  policy = data.aws_iam_policy_document.bucket_policy.json
}

resource "aws_iam_role_policy_attachment" "iam_policy_document" {
  role       = aws_iam_role.bucket_role.name
  policy_arn = aws_iam_policy.iam_policy.arn
}


