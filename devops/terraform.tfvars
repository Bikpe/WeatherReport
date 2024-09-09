bucket_name   = "my-s3-bucket-bi001"
aws_iam_role  = "tf-weather-report-role"
aws_iam_policy = "tf-iam-role-policy-12345"
bucket_policy = {
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::my-s3-bucket-bi001/*",
      "Principal": "*"
    }
  ]
}
