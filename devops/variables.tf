variable "bucket_name" {
  description = "bucket name"
}

variable "aws_iam_role" {
  description = "bucket IAM role"
}

variable "versioning" {
  description = "enable/disable bucket versioning"
  default = false
}

variable "object_lock" {
  description = "enable/disable bucket object locks"
  default = false
}

variable "bucket_policy" {
  description = "bucket_policy"
  default = {}
}

variable "accelerate" {
  description = "enable/disable bucket acceleration"
  default = false
}

variable "public_access" {
  description = "enable/disable public access"
  default = false
}


variable "aws_iam_policy" {
  type = string
}

