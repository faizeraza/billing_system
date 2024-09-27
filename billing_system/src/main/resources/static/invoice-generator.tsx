"use client"

import { useState } from "react"
import { Plus, Trash2 } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"

interface Product {
  name: string
  price: number
  quantity: number
}

export default function InvoiceGenerator() {
  const [customerName, setCustomerName] = useState("")
  const [customerPhone, setCustomerPhone] = useState("")
  const [customerAddress, setCustomerAddress] = useState("")
  const [products, setProducts] = useState<Product[]>([{ name: "", price: 0, quantity: 1 }])

  const addProduct = () => {
    setProducts([...products, { name: "", price: 0, quantity: 1 }])
  }

  const removeProduct = (index: number) => {
    setProducts(products.filter((_, i) => i !== index))
  }

  const updateProduct = (index: number, field: keyof Product, value: string | number) => {
    const updatedProducts = [...products]
    updatedProducts[index] = { ...updatedProducts[index], [field]: value }
    setProducts(updatedProducts)
  }

  const calculateTotal = () => {
    return products.reduce((total, product) => total + product.price * product.quantity, 0)
  }

  return (
    <Card className="w-full max-w-4xl mx-auto">
      <CardHeader>
        <CardTitle className="text-2xl font-bold">Invoice Generator</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <Label htmlFor="customerName">Customer Name</Label>
              <Input
                id="customerName"
                value={customerName}
                onChange={(e) => setCustomerName(e.target.value)}
                placeholder="Enter customer name"
              />
            </div>
            <div>
              <Label htmlFor="customerPhone">Phone</Label>
              <Input
                id="customerPhone"
                value={customerPhone}
                onChange={(e) => setCustomerPhone(e.target.value)}
                placeholder="Enter phone number"
              />
            </div>
            <div>
              <Label htmlFor="customerAddress">Address</Label>
              <Input
                id="customerAddress"
                value={customerAddress}
                onChange={(e) => setCustomerAddress(e.target.value)}
                placeholder="Enter address"
              />
            </div>
          </div>

          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Product Name</TableHead>
                <TableHead>Price</TableHead>
                <TableHead>Quantity</TableHead>
                <TableHead>Amount</TableHead>
                <TableHead></TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {products.map((product, index) => (
                <TableRow key={index}>
                  <TableCell>
                    <Input
                      value={product.name}
                      onChange={(e) => updateProduct(index, "name", e.target.value)}
                      placeholder="Product name"
                    />
                  </TableCell>
                  <TableCell>
                    <Input
                      type="number"
                      value={product.price}
                      onChange={(e) => updateProduct(index, "price", parseFloat(e.target.value))}
                      placeholder="Price"
                    />
                  </TableCell>
                  <TableCell>
                    <Input
                      type="number"
                      value={product.quantity}
                      onChange={(e) => updateProduct(index, "quantity", parseInt(e.target.value))}
                      placeholder="Quantity"
                    />
                  </TableCell>
                  <TableCell>${(product.price * product.quantity).toFixed(2)}</TableCell>
                  <TableCell>
                    <Button variant="destructive" size="icon" onClick={() => removeProduct(index)}>
                      <Trash2 className="h-4 w-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>

          <Button onClick={addProduct} className="mt-2">
            <Plus className="mr-2 h-4 w-4" /> Add Product
          </Button>
        </div>
      </CardContent>
      <CardFooter className="flex justify-between items-center">
        <div className="text-lg font-semibold">Total Amount:</div>
        <div className="text-2xl font-bold">${calculateTotal().toFixed(2)}</div>
      </CardFooter>
    </Card>
  )
}