package br.com.lucascordeiro.klever.data.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}