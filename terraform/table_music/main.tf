resource "aws_dynamodb_table" "basic-dynamodb-table" {
  name           = "Product"
  billing_mode   = "PROVISIONED"
  read_capacity  = 20
  write_capacity = 20
  hash_key       = "id"
  range_key      = "dataCadastro"

  attribute {
    name = "id"
    type = "S"
  }

  attribute {
    name = "dataCadastro"
    type = "S"
  }

  attribute {
    name = "categoria"
    type = "S"
  }

  global_secondary_index {
    name               = "categoria-index"
    hash_key           = "id"
    range_key           = "categoria"
    write_capacity     = 5
    read_capacity      = 10
    projection_type    = "ALL"
  }

  tags = {
    Name        = "dynamodb-table-1"
    Environment = "production"
  }
}