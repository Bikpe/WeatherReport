variable "bucket_name" {
  description = "bucket name"
}

variable "acl" {
  description = "bucket acl"
  default = "private"
}

variable "versioning" {
  description = "enable/disable bucket versioning"
  default = false
}

variable "object_lock" {
  description = "enable/disable bucket object locks"
  default = false
}